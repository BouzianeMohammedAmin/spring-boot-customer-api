import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Button, ButtonGroup } from '@chakra-ui/react'
import SidebarWithHeader from './shared/SideBar'

function App() {
  const [count, setCount] = useState(0)

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
