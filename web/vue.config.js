module.exports = {
  devServer: {
    proxy: {
      "/": {
        target: "http://localhost:8081",
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          "^/": ""
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
