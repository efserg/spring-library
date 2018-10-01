import React, {Component} from 'react';

class Book extends Component {
    render() {
        return (
            <div className="book">
                <h2 className="book_title">{this.props.book.title}</h2>
                <p className="book_isbn">{this.props.book.isbn} - {this.props.book.year}</p>
                <button className="edit"
                        onClick={() => this.props.dispatch({type: 'EDIT_BOOK', id: this.props.book.id})}>Edit
                </button>
                <button className="delete"
                        onClick={() => this.props.dispatch({type: 'DELETE_BOOK', id: this.props.book.id})}>Delete
                </button>
            </div>
        );
    }
}

export default Book;