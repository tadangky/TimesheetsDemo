package com.tadev.android.timesheets.ui

import com.google.gson.Gson
import com.tadev.android.timesheets.data.model.Job
import com.tadev.android.timesheets.data.model.Row

//[{"block":"block 1","orchard":"orchard 1","rate":10,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 100","other_num":28},{"active":false,"current":2,"max":54,"name":"4","other":"Other 1","other_num":3},{"active":true,"current":2,"max":343,"name":"5","other":"Other 1","other_num":67}],"staff":"Bobbbb sdhfusd","type":1},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":2,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"asfdasf","type":1},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"asfasf","type":1},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"ZZZZZ","type":1},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"kkkk","type":1},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"bbbbb","type":2},{"block":"block 1","orchard":"orchard 1","rate":0,"rate_type":1,"row":[{"active":true,"current":2,"max":256,"name":"3","other":"Other 1","other_num":28}],"staff":"ghrfhg","type":2}]

val json =
    "[{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":10,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 100\",\"other_num\":28},{\"active\":false,\"current\":2,\"max\":54,\"name\":\"4\",\"other\":\"Other 1\",\"other_num\":3},{\"active\":true,\"current\":2,\"max\":343,\"name\":\"5\",\"other\":\"Other 1\",\"other_num\":67}],\"staff\":\"Bobbbb sdhfusd\",\"type\":1},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":2,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"asfdasf\",\"type\":1},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"asfasf\",\"type\":1},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"ZZZZZ\",\"type\":1},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"kkkk\",\"type\":1},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"bbbbb\",\"type\":2},{\"block\":\"block 1\",\"orchard\":\"orchard 1\",\"rate\":0,\"rate_type\":1,\"row\":[{\"active\":true,\"current\":2,\"max\":256,\"name\":\"3\",\"other\":\"Other 1\",\"other_num\":28}],\"staff\":\"ghrfhg\",\"type\":2}]"

fun genFakeData(): List<Job> {
    return listOf(
        Job(
            type = 1,
            staffName = "Bobbbb sdhfusd",
            orchard = "orchard 1",
            block = "block 1",
            rateType = 1,
            rate = 10,
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 100", otherNumber = 28),
                Row(
                    name = "4",
                    max = 54,
                    current = 2,
                    other = "Other 1",
                    otherNumber = 3,
                    active = false
                ),
                Row(name = "5", max = 343, current = 2, other = "Other 1", otherNumber = 67)
            )
        ),
        Job(
            type = 1,
            staffName = "asfdasf",
            orchard = "orchard 1",
            block = "block 1",
            rateType = 2,
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
        Job(
            type = 1,
            staffName = "asfasf",
            orchard = "orchard 1",
            block = "block 1",
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
        Job(
            type = 2,
            staffName = "bbbbb",
            orchard = "orchard 1",
            block = "block 1",
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
        Job(
            type = 1,
            staffName = "ZZZZZ",
            orchard = "orchard 1",
            block = "block 1",
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
        Job(
            type = 2,
            staffName = "ghrfhg",
            orchard = "orchard 1",
            block = "block 1",
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
        Job(
            type = 1,
            staffName = "kkkk",
            orchard = "orchard 1",
            block = "block 1",
            row = listOf(
                Row(name = "3", max = 256, current = 2, other = "Other 1", otherNumber = 28)
            )
        ),
    )
}

fun genListFromJson() {
    val list = Gson().fromJson(json, Array<Job>::class.java).asList()
}