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
  // css: {
  //   loaderOptions: {
  //     less: {
  //       modifyVars: {
  //         // less vars，customize ant design theme

  //         // 'primary-color': '#F5222D',
  //         // 'link-color': '#F5222D',
  //         "border-radius-base": "2px"
  //       },
  //       // DO NOT REMOVE THIS LINE
  //       javascriptEnabled: true
  //     }
  //   }
  // }
};
