package com.example.marvelapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelapp.database.entity.CharacterWithBookMarkEntity
import com.example.marvelapp.databinding.RawCharacterItemBinding
import com.example.marvelapp.util.Constants.BOOK_MARK
import com.example.marvelapp.util.Constants.ROOT
import java.util.Locale

class CharacterAdapter(
    val characters: MutableList<CharacterWithBookMarkEntity>,
    val listener: (message: String?, data: CharacterWithBookMarkEntity) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder?>(), Filterable {
    private val filterCharacters: MutableList<CharacterWithBookMarkEntity> =
        characters.toMutableList()
    private var filterCharacter: FilterCharacter? = null
    private var query = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val li: LayoutInflater = LayoutInflater.from(parent.context)
        return CharacterViewHolder(RawCharacterItemBinding.inflate(li, parent, false))
    }

    override fun getItemCount() = filterCharacters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(position)
    }

    fun setData(characters: List<CharacterWithBookMarkEntity>?) {
        this.characters.clear()
        this.characters.addAll(characters!!)
        filter.filter(query)
    }

    override fun getFilter(): Filter {
        if (filterCharacter == null) {
            filterCharacter = FilterCharacter()
        }
        return filterCharacter!!
    }

    private inner class FilterCharacter : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val filterResults = FilterResults()
            if (charSequence.isEmpty()) {
                query = ""
                filterResults.values = characters
                filterResults.count = characters.size
            } else {
                query = charSequence.toString().uppercase(Locale.getDefault())
                val filterList: ArrayList<CharacterWithBookMarkEntity> =
                    ArrayList()
                for (characterWithBookMark in characters) {
                    val characterName =
                        characterWithBookMark.character?.name?.uppercase(Locale.getDefault()) ?: ""
                    if (characterName.contains(query)) {
                        filterList.add(characterWithBookMark)
                    }
                }
                filterResults.values = filterList
                filterResults.count = filterList.size
            }
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            setFilterData(filterResults.values as List<CharacterWithBookMarkEntity>)
        }
    }

    fun setFilterData(characters: List<CharacterWithBookMarkEntity>) {
        if (filterCharacters.isEmpty()) {
            notifyDataSetChanged()
        }
        val cdc = CharacterDiffCallback(filterCharacters, characters)
        val diffResult: DiffResult = DiffUtil.calculateDiff(cdc)
        filterCharacters.clear()
        filterCharacters.addAll(characters)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CharacterViewHolder(private val binding: RawCharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val character: CharacterWithBookMarkEntity = filterCharacters[position]
            binding.character = character
            binding.mcvRoot.setOnClickListener { listener.invoke(ROOT, character) }
            binding.cbBookmark.setOnClickListener { listener.invoke(BOOK_MARK, character) }
        }
    }
}
