export default {
  changeTheme(state, theme) {
    state.theme = theme;
    window.localStorage.setItem("theme", theme);
  },
  signIn(state, user) {
    user.organizations = Object.fromEntries(
      user.organizations.map(o => [
        o.id,
        {
          ...o,
          roles: Object.fromEntries(
            o.roles.map(r => [
              r.id,
              {
                ...r,
                permissions: Object.fromEntries(
                  r.permissions.map(p => [p.id, p])
                )
              }
            ])
          )
        }
      ])
    );
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
  },
  updatePublish(state, publish) {
    state.publish = publish;
  },
  subscribes(state, subscribes) {
    state.subscribes = subscribes;
  }
};
