package ca.uqac.programmationmobile.messages.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import ca.uqac.programmationmobile.messages.R
import ca.uqac.programmationmobile.messages.data.UserDataSource
import ca.uqac.programmationmobile.messages.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.lang.Exception
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class QrScanner : Fragment() {

    private lateinit var previewView: PreviewView
    private lateinit var cameraExecutor: Executor
    private lateinit var barcodeOptions: BarcodeScannerOptions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(isCameraAllowed()){
            startCamera()
        } else {
            if(activity is MainActivity){
                (activity as MainActivity).askForPermission(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSION) { code, permission, granted ->
                    if(code == REQUEST_CODE_PERMISSION && granted) {
                        startCamera()
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_qr_scanner, container, false)

        previewView = view.findViewById(R.id.camera_preview)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        barcodeOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
    }

    private fun isCameraAllowed() : Boolean{
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.createSurfaceProvider())
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setTargetRotation(previewView.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, QRAnalyzer(barcodeOptions) { result ->
                        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
                        UserDataSource(requireContext()).addFriend(account!!.id, result)
                        findNavController().navigateUp()
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            } catch (ex: Exception){
                Log.e(TAG, "Unable to bind lifecycle", ex)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private class QRAnalyzer(val options: BarcodeScannerOptions, val callback : (String) -> Unit) : ImageAnalysis.Analyzer {
        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val media = imageProxy.image
            if(media == null){
                imageProxy.close()
                return
            }

            //We have an image
            val image = InputImage.fromMediaImage(media, imageProxy.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)
            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    //We got a barcode
                    for(barcode in barcodes){
                        Log.d(TAG, "QR Code : ${barcode.rawValue}")
                        callback(barcode.rawValue)
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "Failed to scan for QR", it)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }

    }

    companion object {
        private const val TAG = "QRScannerFrag"
        private const val REQUEST_CODE_PERMISSION = 42
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}