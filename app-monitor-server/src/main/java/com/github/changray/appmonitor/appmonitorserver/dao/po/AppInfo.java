package com.github.changray.appmonitor.appmonitorserver.dao.po;


import javax.persistence.*;

@Table(name = "app_info")
@Entity
public class AppInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
