package listener

interface CardAnimalListener {
    fun OnEditClicked(position: Int)
    fun OnDeleteClicked(position: Int)
    fun OnInteractionClicked(position: Int)
    fun OnFeedClicked(position: Int)
}