import React, {Component} from 'react';

import {connect} from 'react-redux';

import Book from './Book'

class AllBook extends Component {
    render() {
        return (
            <div>
                <h1>All Books</h1>
                {this.props.books.map((book) => <Book key={book.id} book={book}/>)}
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        books: state
    }
};

export default connect(mapStateToProps)(AllBook);
