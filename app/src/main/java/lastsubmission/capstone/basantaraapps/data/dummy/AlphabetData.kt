package lastsubmission.capstone.basantaraapps.data.dummy

object AlphabetData {
    fun getAlphabetData(): List<Alphabet> {
        val alphabetData = arrayListOf<Alphabet>()

        val dummyA = Alphabet(
             "a",
        "https://storage.googleapis.com/basantara-assets/images-vector/a.png",
         "Regangkan jari telunjuk dan jempol kedua tangan dan tempelkan satu sama lain sehingga membentuk segitiga."
        )



        alphabetData.add(dummyA)

        return alphabetData
    }


}