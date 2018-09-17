import React, {Component} from 'react';
import BookForm from "./BookForm";
import AllBook from "./AllBook";

class App extends Component {
    render() {
        return (
            <div className="App">
                <BookForm/>
                <AllBook/>
            </div>);
    }
}

export default App;