package showkooraadmin.ka.showkooraadmin

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_add_world_news.*
import showkooraadmin.ka.showkooraadmin.model.NewModel
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class AddWorldNewsActivity : AppCompatActivity() {

    private var imageUri: Uri? = null


    private var selectedImage: Bitmap? = null

    lateinit var storageReference: FirebaseStorage
    lateinit var database: FirebaseDatabase
    lateinit var cityNew: NewModel

    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_world_news)
        storageReference = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("جاري رفع الخبر..")



        worldNewsIV.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(this@AddWorldNewsActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this@AddWorldNewsActivity, "Permission Denied", Toast.LENGTH_LONG).show()
                    ActivityCompat.requestPermissions(this@AddWorldNewsActivity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)

                } else {

                    CheckUserPermsions()
                }

            } else {
                CheckUserPermsions()
            }


        }


        worldNewsAddBTN.setOnClickListener {

            if (imageUri == null) {
                Toast.makeText(applicationContext, "ادخل صورة الخبر ", Toast.LENGTH_LONG).show()
            } else if (worldNewsTitleET.text.isEmpty()) {
                Toast.makeText(applicationContext, "ادخل عنوان الخبر ", Toast.LENGTH_LONG).show()


            } else if (worldNewsdescET.text.isEmpty()) {
                Toast.makeText(applicationContext, "ادخل وصف الخبر ", Toast.LENGTH_LONG).show()

            } else {
                progressDialog.show()
                uploadeImage()
            }

        }

    }

    private fun getImageFromAlbum() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, 200)

    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {
            try {
                imageUri = data!!.data
                val imageStream = contentResolver.openInputStream(imageUri!!)
                selectedImage = BitmapFactory.decodeStream(imageStream)

                worldNewsIV.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this@AddWorldNewsActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(this@AddWorldNewsActivity, "You haven't picked Image", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadeImage() {
        imageUri?.let {
            storageReference.getReference(UUID.randomUUID().toString()).putFile(it).addOnSuccessListener({

                imageUri = it.downloadUrl!!
                val c = Calendar.getInstance().time
                val df = SimpleDateFormat("dd/MM/yyyy")
                val formattedDate = df.format(c)
                cityNew = NewModel(imageUri.toString(), worldNewsTitleET.text.toString(), worldNewsdescET.text.toString(), formattedDate)
                database.getReference("WorldNews").push().setValue(cityNew)
                worldNewsIV.setImageResource(android.R.drawable.ic_input_add)
                worldNewsTitleET.text.clear()
                worldNewsdescET.text.clear()
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "تم رفع الخبر بنجاح ", Toast.LENGTH_LONG).show()


            }).addOnFailureListener({
                Toast.makeText(applicationContext, "" + it.message.toString(), Toast.LENGTH_LONG).show()
            })
        }
    }


    fun CheckUserPermsions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE_ASK_PERMISSIONS)
                return
            }
        }

        getImageFromAlbum()// init the contact list

    }

    //get acces to location permsion
    private val REQUEST_CODE_ASK_PERMISSIONS = 123


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImageFromAlbum()// init the contact list
            } else {
                // Permission Denied
                Toast.makeText(this, "your message", Toast.LENGTH_SHORT)
                        .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


}


