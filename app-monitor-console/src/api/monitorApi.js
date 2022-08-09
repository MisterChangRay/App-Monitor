import { find, map, random } from 'lodash'
import faker from 'faker/locale/zh_CN'
import { service, serviceForMock, request, requestForMock, mock } from '@/api/_service.js'
import * as tools from '@/api/_tools.js'


/**
 * @description 列表
 */
export function CLIENT_INFO_LIST (param) {
  return request({
    url: '/server/list',
    method: 'get',
    params: param
  })
}


/**
 * @description 列表
 */
export function CLIENT_INFO_ADD (param) {
  return request({
    url: '/server/add',
    method: 'post',
    data: param
  })
}

/**
 * @description 列表
 */
export function CLIENT_INFO_EDIT (param) {
  return request({
    url: '/server/edit',
    method: 'post',
    data: param
  })
}


/**
 * @description 列表
 */
export function CLIENT_INFO_DEL (param) {
  return request({
    url: '/server/del',
    method: 'post',
    data: param
  })
}

