package lastsubmission.capstone.basantaraapps.data.dummy

object LoginData {
    fun getLoginData(): List<Login> {
        val loginData = arrayListOf<Login>()
        loginData.add(Login(
            email = "john.doe@example.com",
            password = "password123"
        ))

        return loginData
    }
}