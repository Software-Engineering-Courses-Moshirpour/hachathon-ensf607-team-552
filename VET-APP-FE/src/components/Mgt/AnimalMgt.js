import { Row,Col,Space, Button,Popconfirm,message,Form,Select   } from 'antd'
import React from 'react'
import Transition from '../Static/Transition'
import { Table } from 'antd';
import { useHistory } from "react-router-dom";
import { useEffect, useState } from 'react';
import axios from '../Api/request';
import moment from 'moment';
import { PENDING,statusData } from '../DummyData/dummy';


const AniamlMgt = () => {
      const { Option } = Select;
      const [animalData, setAnimalData] = useState([]);
      const [userData, setUserData] = useState([]);
      const [uid, setuid] = useState(0);
      let history = useHistory();

      //const data = [];
      const [data, setData] = useState([]);

      useEffect(() => {
        loadAnimal();
        loadUser();
      }, [])

      useEffect(() => {
        const temp = animalData.map(ad => (
          {...ad,
          key:ad.id,
          dob: moment(new Date(ad.dob)).format('YYYY-MM-DD'),
          requestTo:null
          }
          ))
          setData(temp);
      }, [animalData])
/*
      animalData.map(ad=>{
        //console.log(ud);
        data.push({
            ...ad,
            key:ad.id,
            age: ad.age,
            dob: moment(new Date(ad.dob)).format('YYYY-MM-DD'),
          })
      });
      */

   
     function reqAnimal(animalID,userID) {
      console.log("reqAnimal");
      console.log(animalID);
      let now = moment().format('YYYY-MM-DD');
      //setUnavailableStatus(animalID);
      console.log(userID);
      //console.log(PENDING);
      //console.log(new Date().format('YYYY-MM-DD'));
      axios.post("api/request/addRequest", {adminstatus:PENDING, reqDate:now,
         returnDate:now, returnedUser:"test",techstatus:PENDING, animalid: animalID, userid:userID})
      .then(res=>{
        console.log(res.data.message);  
      })


      //console.log(uid);
      //console.log(key);
      //.post
      /*axios.get("api/animal/setUnavailableStatus?id="+key)
      .then(res=>{
        //console.log(res.data.message);  
      })
      .then(res=>{
        loadAnimal();
        //console.log(uid);
      })*/
    }
    //console.log(data);
    function setUnavailableStatus(key){
      axios.get("api/animal/setUnavailableStatus?id="+key)
      .then(res=>{
        console.log(res.data.message);  
      })
      .then(res=>{
        loadAnimal();
      })
    }
    const handleChange = (value,v1,record) => {
      record.requestTo = value
      //setuid(value);  
      let dataTemp = data
      let rowIndex = dataTemp.find((item) => item.id = record.id)
      dataTemp.splice(rowIndex, 1, record);
      setData(dataTemp)
      //console.log(value);
      //console.log(v1);
      //console.log(record.key);
      //console.log(value);
      //console.log(uid);
    };
    const handleRequest = (record) => {
      reqAnimal(record.id,record.requestTo);
    };

    function loadAnimal(){
      axios.get("api/animal/getAvailableAnimal")
      .then(res=>{
        setAnimalData(res.data.data);  
      })
    }
    function loadUser(){
      axios.get("api/user/getAllUserByRole")
      .then(res=>{
        setUserData(res.data.data);  
        //console.log(res.data.data);
      })
      
    }
    const columns = [
        {
          title: 'ID',
          dataIndex: 'id',
          
        },
          {
            title: 'Age',
            dataIndex: 'age',
          },
        {
          title: 'Breed',
          dataIndex: 'breed',
        },
        {
          title: 'Status',
          dataIndex: 'status',
        }, 
        {
          title: 'Request to',
          dataIndex: 'userid',
          render:(text,record) => (
              <Select
              placeholder="Select a technician"
              //value={record.requestTo}
              //onChange = {handleChange}
              onChange={(e)=>handleChange(e, text, record)}
              allowClear
              >
              {userData.map(rl =>(
                  <Option key={rl.id} value={rl.id}>{rl.id}</Option>
              ))}
              </Select>
          )
        },
        {
          title: 'Action',
          key: 'action',
          render: (text, record) => (
            <Space size="middle">
              
              <Button onClick={() => handleRequest(record)}>Request</Button>
              <Button>Cancle</Button>
            </Space>
          ),
        },
      ];

 
// <Button onClick={() => reqAnimal(record.key)}>Request</Button>

    return (
        <React.Fragment>
        <Row>
        <Col span={4} style={{marginTop:"-80px"}}>
        <Transition></Transition>
        </Col>
        <Col span={16} style={{marginTop:"20px"}}>
           <h1>Animal Management</h1>
           <Table bordered columns={columns} dataSource={data} />
        </Col>
       
        </Row>
        </React.Fragment>
    )
}


export default AniamlMgt;
