import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/console/monitor',
  name: 'console-components-monitor-index',
  meta,
  redirect: { name: 'console-components-monitor-index' },
  component: layoutHeaderAside,
  children: [
    { path: 'index', name: 'console-components-monitor-index', component: _import('console/monitor/index.vue'), meta: { ...meta, title: '首页大屏' } },
    { path: 'applist/index', name: 'console-components-monitor-applist-index', component: _import('console/monitor/applist/index.vue'), meta: { ...meta, title: '应用列表' } },
    { path: 'serverlist/index', name: 'console-components-monitor-server-list-index', component: _import('console/monitor/serverlist/index.vue'), meta: { ...meta, title: '服务器列表' } },

  ]
}
