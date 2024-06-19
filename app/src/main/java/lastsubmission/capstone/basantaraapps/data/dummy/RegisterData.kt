package lastsubmission.capstone.basantaraapps.data.dummy

object RegisterData {
    fun getRegisterData(): List<Register> {
        val registerData = arrayListOf<Register>()
        // Menambahkan data pendaftaran ke dalam registerData
        registerData.add(Register(
            name = "John Doe",
            email = "john.doe@example.com",
            password = "password123"
        ))
        registerData.add(Register(
            name = "Jane Smith",
            email = "jane.smith@example.com",
            password = "password456"
        ))
        registerData.add(Register(
            name = "Michael Johnson",
            email = "michael.johnson@example.com",
            password = "password789"
        ))
        registerData.add(Register(
            name = "Emily Davis",
            email = "emily.davis@example.com",
            password = "password012"
        ))
        registerData.add(Register(
            name = "William Brown",
            email = "william.brown@example.com",
            password = "password345"
        ))

        return registerData

    }
}