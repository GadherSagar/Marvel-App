package com.example.marvelapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity

class CharacterDiffCallback(
    private val oldList: List<CharacterWithBookMarkEntity>,
    private val newList: List<CharacterWithBookMarkEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].character?.id
            .equals(newList[newItemPosition].character?.id)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCharacterWithBookMarkEntity: CharacterWithBookMarkEntity = oldList[oldItemPosition]
        val newCharacterWithBookMarkEntity: CharacterWithBookMarkEntity = newList[newItemPosition]

        val isNameMatch =
            oldCharacterWithBookMarkEntity.character?.name.equals(newCharacterWithBookMarkEntity.character?.name)
        val isBookMarkMatch =
            oldCharacterWithBookMarkEntity.bookMark?.bookMark === newCharacterWithBookMarkEntity.bookMark?.bookMark
        return isNameMatch && isBookMarkMatch
    }
}
