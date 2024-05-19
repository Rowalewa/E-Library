package com.example.e_library.models

class BorrowingBook {
    var clientId: String = ""
    var bookId: String = ""
    var bookTitle: String = ""
    var bookAuthor: String = ""
    var bookISBNNumber: String = ""
    var bookGenre: String = ""
    var bookPublisher: String = ""
    var bookSynopsis: String = ""
    var bookImageUrl: String = ""
    var borrowDate: String = ""
    var returnDate: String = ""

    constructor(
        clientId: String,
        bookId: String,
        bookTitle: String,
        bookAuthor: String,
        bookISBNNumber: String,
        bookGenre: String,
        bookPublisher: String,
        bookSynopsis: String,
        bookImageUrl: String,
        borrowDate: String,
        returnDate: String
    ) {
        this.clientId = clientId
        this.bookId = bookId
        this.bookTitle = bookTitle
        this.bookAuthor = bookAuthor
        this.bookISBNNumber = bookISBNNumber
        this.bookGenre = bookGenre
        this.bookPublisher = bookPublisher
        this.bookSynopsis = bookSynopsis
        this.bookImageUrl = bookImageUrl
        this.borrowDate = borrowDate
        this.returnDate = returnDate
    }

    constructor()
}