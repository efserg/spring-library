import React, {Component} from 'react';
import BookForm from "./BookForm";
import AllBook from "./AllBook";

class App extends Component {
    render() {
        return (
            <div className="App">
                <div className="navbar">
                    <h2 className="center">Add book</h2>
                </div>
                <BookForm/>
                <AllBook/>
            </div>);
    }
}

export default App;