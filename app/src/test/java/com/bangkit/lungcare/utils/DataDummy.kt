package com.bangkit.lungcare.utils

import com.bangkit.lungcare.domain.model.xray.Xray

object DataDummy {
    fun generateDummyXrayEntity(): List<Xray> {
        val xrayList = ArrayList<Xray>()
        for (i in 0..10) {
            val xray = Xray(
                i.toString(),
                "2023-06-08T00:18:26.000Z",
                "processResult $i",
                "https://storage.googleapis.com/bucket-upload-testing/daniel kusuma_1686208696204"
            )
            xrayList.add(xray)
        }
        return xrayList
    }
}