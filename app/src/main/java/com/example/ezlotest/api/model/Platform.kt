package com.example.ezlotest.api.model

import com.example.ezlotest.R

enum class Platform(val value: String, val imageRes: Int) {
    SERCOMM_G450("Sercomm G450", R.mipmap.ic_vera_plus_big),
    SERCOMM_G550("Sercomm G550", R.mipmap.ic_vera_secure_big),
    MICASAVERDE_VERALITE("MiCasaVerde VeraLite", R.mipmap.ic_vera_edge_big),
    SERCOMM_NA900("Sercomm NA900", R.mipmap.ic_vera_edge_big),
    SERCOMM_NA301("Sercomm NA301", R.mipmap.ic_vera_edge_big),
    SERCOMM_NA930("Sercomm NA930", R.mipmap.ic_vera_edge_big),
    UNKNOWN("no platform key", R.mipmap.ic_vera_edge_big)
}