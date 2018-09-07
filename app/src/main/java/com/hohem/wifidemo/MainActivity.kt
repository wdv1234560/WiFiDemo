package com.hohem.wifidemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_wifi.view.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    var mWifiList = ArrayList<ScanResult>()
    var mAllWifis = ArrayList<ScanResult>()
    var mWifiManager: WifiManager? = null
    @SuppressLint("WifiManagerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        mWifiList.toMutableList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = WifiAdapter(mWifiList)
        mWifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager?

        button.setOnClickListener {

            checkState(this)
        }
    }

    // 检查当前WIFI状态     
    fun checkState(context: Context) {
        if (mWifiManager?.wifiState == 0) {
            Toast.makeText(context, "Wifi正在关闭", Toast.LENGTH_SHORT).show()
        } else if (mWifiManager?.wifiState == 1) {
            Toast.makeText(context, "Wifi已经关闭", Toast.LENGTH_SHORT).show()
        } else if (mWifiManager?.wifiState == 2) {
            Toast.makeText(context, "Wifi正在开启", Toast.LENGTH_SHORT).show()
        } else if (mWifiManager?.wifiState == 3) {
            Toast.makeText(context, "Wifi已经开启", Toast.LENGTH_SHORT).show()
            applypermission()
        } else {
            Toast.makeText(context, "没有获取到WiFi状态", Toast.LENGTH_SHORT).show()
        }
    }

    var mDisposable:Disposable? = null
    @SuppressLint("MissingPermission")
    fun scanWifi() {

        Observable.interval(1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Observer<Long>{
                    override fun onNext(value: Long?) {
                        if(value!! >9){
                            mDisposable?.dispose()
                            return
                        }
                        Log.d("WIFI","次数："+value)
                        mWifiManager?.startScan()
                        val scanResults = mWifiManager?.scanResults!!
                        mAllWifis.addAll(scanResults)
                        mWifiList.clear()
                        mWifiList.addAll(getWifiListAll())
                        rv.adapter.notifyDataSetChanged()
                    }

                    override fun onSubscribe(d: Disposable?) {
                        mDisposable = d
                    }

                    override fun onComplete() {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                })

    }

    /**
     * 得到网络列表
     *
     * @return the wifi list all
     */
    fun getWifiListAll(): java.util.ArrayList<ScanResult> {
        val newSr = java.util.ArrayList<ScanResult>()
        for (result in mAllWifis) {
            if (!TextUtils.isEmpty(result.SSID) && !result.capabilities.contains("[IBSS]") && !containName(newSr, result))
                newSr.add(result)
        }
        return newSr
    }

    /**
     * 判断一个扫描结果中，是否包含了某个名称的WIFI
     *
     * @param sr
     * 扫描结果
     * @param scanResult
     * 要查询的名称
     *
     * @return 返回true表示包含了该名称的WIFI ，返回false表示不包含
     */
    fun containName(sr: List<ScanResult>, scanResult: ScanResult): Boolean {
        for (result in sr) {
            if (!TextUtils.isEmpty(result.SSID) && result.SSID == scanResult.SSID && result.capabilities == scanResult.capabilities) {
                return true
            }
        }
        return false
    }


    fun applypermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查是否已经给了权限
            val checkpermission = ContextCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            if (checkpermission == PackageManager.PERMISSION_GRANTED) {//已授权
                scanWifi()
            } else {//

                //参数分别是当前活动，权限字符串数组，requestcode
                Log.e("permission", "动态申请")
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //grantResults数组与权限字符串数组对应，里面存放权限申请结果
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this@MainActivity, "已授权", Toast.LENGTH_SHORT).show()
            scanWifi()
        } else {
            Toast.makeText(this@MainActivity, "拒绝授权", Toast.LENGTH_SHORT).show()
        }
    }

    class WifiAdapter(val list: List<ScanResult>) : RecyclerView.Adapter<WifiAdapter.WifiHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wifi, parent, false)
            return WifiHolder(view)
        }

        override fun getItemCount(): Int = list.size


        override fun onBindViewHolder(holder: WifiHolder, position: Int) {
            holder.itemView.textView.text = list.get(position).SSID
        }

        class WifiHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        }

    }
}
