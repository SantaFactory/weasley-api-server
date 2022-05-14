package com.weasleyclock.weasleyclient.enmus

enum class UserType(var value: String, var desc: String) {

    DEFAULT("D", "application user"),
    GOOGLE("G", "google user");

    companion object {

        /**
         * static method
         */
        fun selectOf(value: String?): UserType {
            return UserType.values().filter { userType: UserType -> userType.value == value }.stream().findFirst()
                .orElse(null)
        }

    }

}
