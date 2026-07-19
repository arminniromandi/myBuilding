package ir.arminniromandi.mybuilding.presentation.viewModel.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note
import ir.arminniromandi.mybuilding.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    val notes: StateFlow<List<Note>> =
        noteRepository.getNotes()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val _isAddDialogVisible = MutableStateFlow(false)
    val isAddDialogVisible: StateFlow<Boolean> = _isAddDialogVisible.asStateFlow()

    private val _noteText = MutableStateFlow("")
    val noteText: StateFlow<String> = _noteText.asStateFlow()

    private val _isFabExpanded = MutableStateFlow(false)
    val isFabExpanded: StateFlow<Boolean> = _isFabExpanded.asStateFlow()



    fun toggleFab() {
        _isFabExpanded.value = !_isFabExpanded.value
    }

    fun openAddDialog() {
        _noteText.value = ""
        _isAddDialogVisible.value = true
        _isFabExpanded.value = false
    }

    fun closeAddDialog() {
        _isAddDialogVisible.value = false
        _noteText.value = ""
    }


    fun onNoteTextChanged(value: String) {
        _noteText.value = value
    }

    fun insertNote() {
        val text = _noteText.value.trim()

        if (text.isBlank()) return

        viewModelScope.launch {
            noteRepository.insertNote(
                Note(
                    Note = text,
                    Checked = false
                )
            )

            _noteText.value = ""
            _isAddDialogVisible.value = false
        }
    }

    fun updateChecked(
        note: Note,
        checked: Boolean
    ) {
        viewModelScope.launch {
            noteRepository.updateNote(
                note.copy(
                    Checked = checked
                )
            )
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
        }
    }
}