import {useEffect} from 'react'
import './App.css'
import { Button } from '@chakra-ui/react'
import SidebarWithHeader from './shared/SideBar'
import {getCostumers} from "./services/Client.jsx";

function App() {

    useEffect(()=>{
        getCostumers().then(res=>{
            console.log(res)
        }).catch(err=>{
            console.log(err)
        })
    },[])

  return (



    <div>
       
        <SidebarWithHeader>
        <Button colorScheme='teal' variant='solid'>
            Button
        </Button>
        </SidebarWithHeader>
    </div>
  )
}

export default App
