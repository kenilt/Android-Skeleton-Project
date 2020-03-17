package com.kenilt.skeleton.model.response

data class ErrorResponse(
    var error: String? = null,
    var errors: MutableList<String>? = null,
    var email: List<String>? = null,
    var password: List<String>? = null,
    var first_name: List<String>? = null,
    var last_name: List<String>? = null,
    var excluded_box_ids: MutableList<Int>? = null) {

    val errorList: MutableList<String>
        get() {
            val arrays = ArrayList<String>()
            errors?.let {
                arrays.addAll(it)
            }
            if (arrays.isEmpty()) {
                error?.let {
                    arrays.add(it)
                }
            }
            return arrays
        }

    val errorString: String
        get() = errorList.joinToString(", ")
}
