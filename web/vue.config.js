module.exports = {
  devServer: {
    proxy: {
      "/user_center": {
        target: "http://localhost:8090",
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/user_center": ""
        }
      }
    }
  }
};
