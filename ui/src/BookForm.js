import React, {Component} from 'react';
import {connect} from 'react-redux';

class BookForm extends Component {

    handleSubmit = (e) => {
        e.preventDefault();
        const title = this.getTitle.value;
        const isbn = this.getIsbn.value;
        const year = this.getYear.value;
        const data = {
            id: new Date(),
            title,
            isbn,
            year
        };

        console.log(data)

        this.props.dispatch({type: 'CREATE_BOOK', data});
        this.getTitle.value = '';
        this.getIsbn.value = '';
        this.getYear.value = '';
    };

    render() {
        return (
            <div>
                <h1>Create Book</h1>
                <form onSubmit={this.handleSubmit}>
                    <input required type="text" ref={(input) => this.getTitle = input} placeholder="Title"/><br/><br/>
                    <input required type="text" ref={(input) => this.getIsbn = input} placeholder="ISBN"/><br/><br/>
                    <input required type="text" ref={(input) => this.getYear = input} placeholder="Year"/><br/><br/>
                    <button>Create</button>
                </form>
            </div>
        );
    }
}

export default connect()(BookForm);
