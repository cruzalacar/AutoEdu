package sheridan.jawedzak.autoedu

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CameraActivity : AppCompatActivity() {

    //private variables
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        //button navigation to take a picture/gain phone permission
        val captureBtn = findViewById<Button>(R.id.capture_btn)
        captureBtn.setOnClickListener{

            //run time permission used for Android Marshmallow or lower
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                //gain permissions for camera settings
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    //permission not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                    //popup to request camera permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //camera permission is granted
                    openCamera()
                }
            }
            else{
                //camera permission is granted for Android Marshmallow and lower
                openCamera()
            }
        }
    }

    //open camera method (retrieving camera)
    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        //camera intent (opening camera from device)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    //requesting to open camera from users' device
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){
                    //permission to open camera is granted
                    openCamera()
                }
                else{
                    //permission to open camera was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //adding captured image to camera activity page (image view from camera xml file)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //image was successfully captured from camera
        if (resultCode == Activity.RESULT_OK){

            //get captured image to image view
            val imageView = findViewById<ImageView>(R.id.image_view)
            imageView.setImageURI(image_uri)
        }
    }
}