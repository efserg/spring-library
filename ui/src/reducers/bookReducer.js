const bookReducer = (state = [], action) => {
    switch (action.type) {
        case 'CREATE_BOOK':
            return state.concat([action.data]);
        case 'DELETE_BOOK':
            return state.filter((book) => book.id !== action.id);
        default:
            return state;
    }
};

export default bookReducer;