export default {
  path: '/console/monitor',
  title: '应用监控',
  icon: 'puzzle-piece',
  children: [
    { path: '/console/monitor/index', title: '首页大屏', icon: 'home' },
    { path: '/console/monitor/applist/index', title: '应用列表', icon: 'tasks' },
    { path: '/console/monitor/serverlist/index', title: '服务器列表', icon: 'hdd-o' },
  ]
}
