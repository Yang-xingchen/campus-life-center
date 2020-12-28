export default {
  signIn(state, user) {
    state.user = user;
    state.token = user.token;
  },
  setSignInId(state, id) {
    state.signInId = id;
  },
  setPubKey(state, key) {
    state.pub_key = key;
  }
};
