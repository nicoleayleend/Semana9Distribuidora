package com.example.distribalimentos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.location.Geocoder
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.widget.TextView
import android.location.Location
import android.Manifest

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.database.DatabaseReference
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import kotlin.math.roundToInt

private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var database: DatabaseReference

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Declarar variables para los componentes de la interfaz
    private lateinit var totalCompra: EditText
    private lateinit var direccionDestino: EditText
    private lateinit var dispatchCostText: TextView
    private lateinit var calculateDispatchBtn: Button
    private var currentLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar los elementos del layout
        totalCompra = findViewById(R.id.totalCompra)
        direccionDestino = findViewById(R.id.direccionDestino)
        dispatchCostText = findViewById(R.id.dispatchCostText)
        calculateDispatchBtn = findViewById(R.id.calculateDispatchBtn)

        // Configurar el botón para buscar dirección y calcular el costo del despacho
        calculateDispatchBtn.setOnClickListener {
            calcularCostoDespacho()
        }

        // Inicializar el fragmento de Google Maps
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Inicializar el cliente de localización
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Verificar si los permisos están concedidos
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            getCurrentLocation()
        } else {
            // Pedir permisos de ubicación si no están concedidos
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.addMarker(MarkerOptions().position(currentLocation!!).title("Ubicación Actual"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 15f))
                    } else {
                        Toast.makeText(this, "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun calcularCostoDespacho() {
        val total = totalCompra.text.toString().toDoubleOrNull()
        val direccion = direccionDestino.text.toString()

        if (total != null && direccion.isNotEmpty() && currentLocation != null) {
            val geocoder = Geocoder(this)
            try {
                val addressList = geocoder.getFromLocationName(direccion, 1)
                if (addressList != null && addressList.isNotEmpty()) {
                    val destinoLatLng = LatLng(addressList[0].latitude, addressList[0].longitude)
                    val results = FloatArray(1)
                    Location.distanceBetween(
                        currentLocation!!.latitude,
                        currentLocation!!.longitude,
                        destinoLatLng.latitude,
                        destinoLatLng.longitude,
                        results
                    )

                    val distanciaKm = results[0] / 1000 // Convertir a kilómetros

                    // Calcular el costo del despacho según las reglas de negocio
                    val costoDespacho = when {
                        total >= 50000 -> 0.0
                        total in 25000.0..49999.99 -> distanciaKm * 150
                        else -> distanciaKm * 300
                    }

                    dispatchCostText.text =
                        "Distancia: ${distanciaKm.roundToInt()} km\nCosto de despacho: $$costoDespacho"
                } else {
                    Toast.makeText(this, "No se encontró la dirección", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error al buscar la dirección", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Ingrese un total válido y una dirección", Toast.LENGTH_SHORT).show()
        }
    }

    // Manejar el resultado de la solicitud de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    mMap.isMyLocationEnabled = true
                    getCurrentLocation()
                }
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val LOCATION_REQUEST_CODE = 101
    }
}


/*companion object {
    var latitude: Double = 0.0;
    var longitude: Double = 0.0;
}

private lateinit var email: EditText
private lateinit var password: EditText
private lateinit var btnLogin: Button
private lateinit var mAuth: FirebaseAuth

override fun onCreate(savedInstanceState: Bundle?) {
    FirebaseApp.initializeApp(this)

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    database = FirebaseDatabase.getInstance().reference

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    requestLocationPermission()

    email = findViewById(R.id.email)
    password = findViewById(R.id.password)
    btnLogin = findViewById(R.id.btnLogin)

    // Inicializar Firebase Auth
    mAuth = FirebaseAuth.getInstance()

    btnLogin.setOnClickListener {
        loginUser()
    }
}

private fun loginUser() {
    val emailInput = email.text.toString()
    val passwordInput = password.text.toString()

    if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(passwordInput)) {
        Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
        return
    }

    mAuth.signInWithEmailAndPassword(emailInput, passwordInput)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                saveLocationToFirebase(latitude, longitude, mAuth.currentUser?.uid.toString() )
                val user = mAuth.currentUser
                updateUI(user)

            } else {
                Toast.makeText(this, "Error en autenticación.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
}

private fun updateUI(user: FirebaseUser?) {
    if (user != null) {
        // Ir a MenuActivity
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}

private val LOCATION_PERMISSION_REQUEST_CODE = 1

private fun requestLocationPermission() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE)
    } else {

        getCurrentLocation()
    }
}

override fun onRequestPermissionsResult(requestCode: Int,
                                        permissions: Array<out String>,
                                        grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            // Permiso concedido
            getCurrentLocation()
        } else {
            // Permiso denegado
            Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
        }
    }
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation() {
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            location?.let {
                latitude = it.latitude
                longitude = it.longitude
                Log.i("MainActivity", "Ubicacion obtenida con exito: $latitude : $longitude")

            }
        }
        .addOnFailureListener {
            Log.i("MainActivity", "Error al obtener ubicacion")
        }
}

private fun saveLocationToFirebase(latitude: Double, longitude: Double, userId: String) {
    val locationData = mapOf(
        "latitude" to latitude,
        "longitude" to longitude,
        "timestamp" to System.currentTimeMillis()
    )

    Log.d("MainActivity", "Entre a saveLocationToFirebase")

    database.child("users").child(userId).child("location").setValue(locationData)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("MainActivity", "Ubicacion guardada de forma exitosa")
            } else {
                Log.d("MainActivity", "No se pudo guardar la ubicacion")
            }
        }
}


}*/
