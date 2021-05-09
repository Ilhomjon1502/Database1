package Utils

import Models.Contact

interface DBServiceInterface {

    fun addContact(contact: Contact)

    fun deleteContact(contact: Contact)

    fun upDataContact(contact: Contact):Int

    fun getAllContact():List<Contact>
}