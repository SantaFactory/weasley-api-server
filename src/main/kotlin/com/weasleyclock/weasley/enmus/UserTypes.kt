package com.weasleyclock.weasley.enmus

enum class UserTypes(var value: String, var desc: String) {
    DEFAULT("D", "application user"),
    GOOGLE("G", "google user");

    companion object {

        /**
         * static method
         */
        fun selectOf(value: String?): UserTypes {
            return UserTypes.values().filter { userTypes: UserTypes -> userTypes.value == value }.stream().findFirst()
                .orElse(null)
        }

    }

}
