import React, {Component} from 'react';

class Book extends Component {
    render() {
        return (
            <div>
                <h2>{this.props.book.title}</h2>
                <p>{this.props.book.isbn} - {this.props.book.year}</p>
                <button>Edit</button>
                <button onClick={() => this.props.dispatch({type: 'DELETE_BOOK', id: this.props.book.id})}>Delete
                </button>
            </div>
        );
    }
}

export default Book;