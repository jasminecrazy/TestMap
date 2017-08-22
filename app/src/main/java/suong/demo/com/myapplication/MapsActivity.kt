package suong.demo.com.myapplication

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var location:Location
    private lateinit var locationManager:LocationManager
    private lateinit var geocoder:Geocoder
    private lateinit var address:List<Address>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        locationManager = this.getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.INTERNET),123)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
mMap.isMyLocationEnabled = true
        if(Utils.isNetworkConnected(applicationContext))
        {
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
            {
                return
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            updateLocation()
        }else
        {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            updateLocation()
        }
    }
    fun checkGPS():Boolean
    {
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }
    fun updateLocation()
    {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude),15f))

        geocoder = Geocoder(this,Locale.getDefault())
        address = geocoder.getFromLocation(location.latitude,location.longitude,1)
        if (address != null && address.size > 0) {
            val addresses:Address = address.get(0)
           val streetAddress:String =addresses.getAddressLine(0)
            mMap.addMarker(MarkerOptions().position(LatLng(location.latitude,location.longitude)).title(streetAddress))
        }


    }

}
