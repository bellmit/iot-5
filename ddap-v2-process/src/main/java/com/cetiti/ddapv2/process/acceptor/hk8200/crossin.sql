
--10.0.30.208:1521/cetiti
--BMS_YJY/hik123456

-- Create table
create table BMS_CROSSING_INFO
(
  crossing_id           NUMBER not null,
  altitude              VARCHAR2(64),
  cascade_id            NUMBER,
  control_unit_id       NUMBER,
  controlunit_indexcode VARCHAR2(32),
  crossing_mode         NUMBER,
  crossing_type         NUMBER,
  enable_relate         NUMBER,
  front_type            NUMBER,
  crossing_index_code   VARCHAR2(64),
  intercity             NUMBER,
  internal_code         NUMBER,
  jccrossingindexcode   VARCHAR2(64),
  lane_num              NUMBER,
  latitude              VARCHAR2(64),
  longitude             VARCHAR2(64),
  crossing_name         VARCHAR2(256),
  photo                 VARCHAR2(256),
  related_camera_name   VARCHAR2(255),
  related_camera_path   VARCHAR2(255),
  crossing_server_id    NUMBER,
  updatetime            TIMESTAMP(6) default sysdate,
  usage_type            NUMBER(10),
  cloudanalysis         NUMBER(10),
  gatcode               VARCHAR2(255 CHAR)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column BMS_CROSSING_INFO.crossing_id
  is '卡口ID';
comment on column BMS_CROSSING_INFO.altitude
  is '海拔 ';
comment on column BMS_CROSSING_INFO.cascade_id
  is '级联ID';
comment on column BMS_CROSSING_INFO.control_unit_id
  is '组织ID，3.0版本后废弃';
comment on column BMS_CROSSING_INFO.controlunit_indexcode
  is '组织编号 ';
comment on column BMS_CROSSING_INFO.crossing_mode
  is '卡口模式 前置 1 后置 2';
comment on column BMS_CROSSING_INFO.crossing_type
  is '卡口类型，目前看来没用处';
comment on column BMS_CROSSING_INFO.enable_relate
  is '是否启用关联视频：0 不启用关联，1 启用关联 ';
comment on column BMS_CROSSING_INFO.front_type
  is '前端类型，相关字典在字典表';
comment on column BMS_CROSSING_INFO.crossing_index_code
  is '卡口编号';
comment on column BMS_CROSSING_INFO.intercity
  is '卡口类型 0普通卡口 1 城际卡口';
comment on column BMS_CROSSING_INFO.internal_code
  is '内部编号';
comment on column BMS_CROSSING_INFO.jccrossingindexcode
  is '稽查布控卡口编号，默认隐藏';
comment on column BMS_CROSSING_INFO.lane_num
  is '车道数 ';
comment on column BMS_CROSSING_INFO.latitude
  is '纬度';
comment on column BMS_CROSSING_INFO.longitude
  is '经度';
comment on column BMS_CROSSING_INFO.crossing_name
  is '卡口名称';
comment on column BMS_CROSSING_INFO.photo
  is '卡口示意图，3.0暂时无用';
comment on column BMS_CROSSING_INFO.related_camera_name
  is '用户选中的监控点名称 ';
comment on column BMS_CROSSING_INFO.related_camera_path
  is '用户之前选中的indexCode';
comment on column BMS_CROSSING_INFO.crossing_server_id
  is '卡口管理服务器id';
comment on column BMS_CROSSING_INFO.updatetime
  is '卡口更新时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table BMS_CROSSING_INFO
  add primary key (CROSSING_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
