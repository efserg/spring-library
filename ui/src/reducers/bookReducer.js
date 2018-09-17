const bookReducer = (state = [], action) => {
    switch (action.type) {
        case 'CREATE_BOOK':
            return state.concat([action.data]);
        default:
            return state;
    }
};
export default bookReducer;