package br.com.fiap.app

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import br.com.fiap.app.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccount : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        mAuth = FirebaseAuth.getInstance()

        btCreate.setOnClickListener {
            if (inputPassword.text.toString() != inputPasswordConfirmation.text.toString()){
                Toast.makeText(this, "Passord don't match!", Toast.LENGTH_SHORT).show()
            }
            mAuth.createUserWithEmailAndPassword(
                inputEmail.text.toString(),
                inputPassword.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    saveInRealTimeDatabase()
                    val intent = Intent(this, AboutActivity::class.java);
                    startActivity(intent)
                } else {
                    Toast.makeText(this@NewAccount, it.exception?.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun saveInRealTimeDatabase() {

        val user = User(inputName.text.toString(), inputEmail.text.toString(), inputPhone.text.toString())
        FirebaseDatabase.getInstance().getReference("Users")
        .child(FirebaseAuth.getInstance().currentUser!!.uid)
        .setValue(user)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                val returnIntent = Intent()
                returnIntent.putExtra("email", inputEmail.text.toString())
                setResult(RESULT_OK, returnIntent)
                finish()
                finish()
            } else {
                Toast.makeText(this, "Erro ao criar o usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }
}