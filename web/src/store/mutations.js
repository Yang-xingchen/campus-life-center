export default {
  changeTheme(state, theme) {
    state.theme = theme;
  },
  signIn(state, user) {
    state.user = user;
    state.token = user.token;
  },
  signOut(state, data) {
    if (data) {
      state.user = null;
      state.token = null;
      window.localStorage.removeItem("token");
    }
  },
  setSignInId(state, id) {
    state.signInId = id;
  },
  setPubKey(state, key) {
    state.pub_key = key;
  },
  setNotice(state, notice) {
    state.notice = notice;
  }
};
