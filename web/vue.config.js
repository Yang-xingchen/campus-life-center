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
      },
      "/todo": {
        target: "http://localhost:10200",
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/todo": ""
        }
      }
    }
  },
  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          // less vars，customize ant design theme

          // 'primary-color': '#F5222D',
          // 'link-color': '#F5222D',
          // "border-radius-base": "2px"
          "text-color": "#FFFFFF",
          "background-color-light": "#FFFFFF20",
          "heading-color": "#FFFFFF",
          "table-selected-row-bg": "#FFFFFF20"
        },
        // DO NOT REMOVE THIS LINE
        javascriptEnabled: true
      }
    }
  }
};
