module.exports = {
  devServer: {
    proxy: {
      "/user_center": {
        target: "http://localhost:10000",
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/user_center": ""
        }
      },
      "/notice": {
        target: "http://localhost:10100",
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/notice": ""
        }
      }
    }
  }
};
