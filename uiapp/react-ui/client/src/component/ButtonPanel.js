import Button from "./Button";
import React from "react";
import PropTypes from "prop-types";
import axios from 'axios';

import "./ButtonPanel.css";

export default class ButtonPanel extends React.Component {
 constructor(props){
    super(props)
    this.responseFromServer={
        content:''
    }
    this.state = {
        document_title:'',
        desc:'',
        publisher: '',
        content:''
    }
}


addDoc=()=>{
    console.log('Clicked on add Document')
  /*
  DocumentService.postApi('create-document',{document_title:this.state.document_title,desc:this.state.desc,publisher:this.state.publisher})
  .then(json => {
  if(json.data.statusCode===200){
  this.props.history.push('/index')
  }
  else{
    alert('something went wrong!!');
  this.props.history.push('/index')
  }
  }).catch((error)=>{
  console.log("error-----------",error)
  })
*/
//this.persistState('Testing message')
this.sendMessage()
console.log('Called addDoc IN AddDOC... ')
}

setResponseFromServer(res){
    console.log('Response from server ',res);
    this.responseFromServer.content=res.content;
}
sendMessage() {
        // POST request using axios with error handling
        
        var messageToService = {"doctitle":this.state.document_title,"desc":this.state.desc,"publisher":this.state.publisher}

        axios.post('/calculate/sendmessage', messageToService)
            .then(response => this.setState({ content: response.data.content }))
            .catch(error => {
                this.setState({ errorMessage: error.message });
                console.error('There was an error!', error);
            });
    }


  persistState = (value) => {
    console.log('Persisting State:');
    console.log(JSON.stringify(value));
    var message = {"data": {"orderId": "33"}}
    
    var messageToService = {"doctitle":this.state.document_title,"desc":this.state.desc,"publisher":this.state.publisher}
    console.log('Message being sent to nodeJS Server ',messageToService)
    const state = [{ 
      key: "calculatorState", 
      value :"testing"
    }];
    
    fetch("/calculate/sendmessage", {
      method: "POST",
      body: JSON.stringify(messageToService),
      headers: {
        "Content-Type": "application/json"
      }
    }).then(response => this.setState({ content: response.data.content }))
    //.then(response => this.setState({ content: response.data.content }))
        

  }
  

handleChange= (e)=> {
  this.setState({[e.target.name]:e.target.value});
}

render() {
    return (
      <div>
      <h2 className="text-center">Enter Information</h2>
      

        <div className="row justify-content-md-center">
          <div className="col-md-6 col-md-offset-3">
            <form>
            <div  class='md-col-12'>
              <div className="boxalg form-group">
                <label>Title:</label>
                <input style={{color: "red",height:"20px"}} name="document_title" type="text" className="form-control" onChange={this.handleChange} value={this.state.document_title}></input>
              </div>

              <div className="boxalg form-group">
                <label>Description:</label>
                <input style={{color: "red",height:"20px"}} name="desc" type="text" className="form-control" onChange={this.handleChange} value={this.state.desc}></input>
              </div>

              <div className="boxalg form-group">
                <label>Publisher:</label>
                <input  style={{color: "red",height:"20px"}} name="publisher" type="text" className="form-control" onChange={this.handleChange} value={this.state.publisher}></input>
              </div>
             

     
              <div className="boxalg form-group">
                <label>Response:</label>
                <input  style={{color: "red",height:"20px",width:"500px"}} name="publisher" type="text" className="form-control"  value={this.state.content}></input>
              </div>
              

             <button  style={{height:"100%"}} type="button" onClick={this.addDoc} className="btn btn-primary">Submitting..</button> 
             
             
             
             </div> 
             
        
             
             
            </form>
          </div>
        </div>
    </div>
    );
  }

}

