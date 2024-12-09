package com.example.ejemplo_requerimiento_permisos

import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejemplo_requerimiento_permisos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mibinding:ActivityMainBinding
    var hora:Int = 0
    var minutos:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mibinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(mibinding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponents()
    }

    private fun initComponents() {
        mibinding.imageButton.setOnClickListener{
            abrirDialog()
        }
        mibinding.button.setOnClickListener {
            if(hora!=0 && minutos!=0) {
                crearAlarma(mibinding.editText.text.toString(), hora, minutos)
            }
            else{
                Toast.makeText(this,"ESTABLE HORA PRIMERO, PULSA EN EL RELOJ",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirDialog() {
     var horadialog=TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
         //Este código se ejecuta una vez seleccionado el valor en el TimePickerDialog
         this.hora=hourOfDay
         this.minutos=minute
         mibinding.textView2.text="$hora:$minutos"
     },
         Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true)
        horadialog.show()
    }

    fun  crearAlarma(mensaje:String,hora:Int,minutos:Int)
    {
        var miIntent= Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE,mensaje)
            putExtra(AlarmClock.EXTRA_HOUR,hora)
            putExtra(AlarmClock.EXTRA_MINUTES,minutos)
        }
        if(miIntent.resolveActivity(packageManager)!=null)
        {
            startActivity(miIntent)
        }
    }
}