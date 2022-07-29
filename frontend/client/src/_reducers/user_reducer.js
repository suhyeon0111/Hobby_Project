import {
    GET_USER,
} from '../_actions/types';
 

export default function(state={},action){
    switch(action.type){
        case GET_USER:
            return {...state, userData: action.payload }
        default:
            return state;
    }
}