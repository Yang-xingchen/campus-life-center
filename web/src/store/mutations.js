export default {
  signIn(state, user) {
    state.user = user;
  },
  setSignId(state, id) {
    state.signInId = id;
  }
};
