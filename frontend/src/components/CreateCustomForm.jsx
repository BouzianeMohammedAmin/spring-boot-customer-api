
import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
import {Alert, AlertIcon, Button, FormLabel, Input, Select, Stack} from "@chakra-ui/react";
import {saveCustomer} from "../services/Client.jsx";
import {notification} from "../services/notification.js";
const MyTextInput = ({ label, ...props }) => {
    // useField() returns [formik.getFieldProps(), formik.getFieldMeta()]
    // which we can spread on <input>. We can use field meta to show an error
    // message if the field is invalid and it has been touched (i.e. visited)
    const [field, meta] = useField(props);
    return (
        <>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Input className="text-input" {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error"  status={"error"} mt={"2"} >
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </>
    );
};



const MySelect = ({ label, ...props }) => {
    const [field, meta] = useField(props);
    return (
        <div>
            <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
            <Select {...field} {...props} />
            {meta.touched && meta.error ? (
                <Alert className="error"  status={"error"} mt={"2"} >
                    <AlertIcon/>
                    {meta.error}
                </Alert>
            ) : null}
        </div>
    );
};

// And now we can use these
const  CreateCustomForm = ({fetchCustomers}) => {
    return (
        <>
            <Formik
                initialValues={{
                    name: '',
                    email: '',
                    age: 0,
                    gender: '', // added for our select
                }}
                validationSchema={Yup.object({
                    name: Yup.string()
                        .max(15, 'Must be 15 characters or less')
                        .required('Required'),
                    email: Yup.string()
                        .email('Invalid email address')
                        .required('Required'),
                    age: Yup.number()
                        .min(16,"your age is < 16 " )
                        .max(120,"must be < 120 ")
                        .required('Required'),

                    gender: Yup.string()
                        .oneOf(
                            ["FEMALE", "MALE"],
                            'Invalid Gender'
                        )
                        .required('Required'),
                })}
                onSubmit={(customer, { setSubmitting }) => {
                    setSubmitting(true) ;

                    saveCustomer(customer)
                            .then(resp=>{
                                console.log(customer) ;
                                fetchCustomers() ;

                                notification("Acc: " +customer.name + " created " ,
                                    "with email : "+ customer.email ,
                                    "success")

                            }).catch(err=>{
                        notification( err.code,
                            err.response.data.message,
                            "error")
                            }).finally(()=>{
                            setSubmitting(false) ;
                        }) ;

                }}
            >
                {({isValid , isSubmitting})=>( <Form>
                    <Stack spacing={"7px"}>
                        <MyTextInput
                            label="Name"
                            name="name"
                            type="text"
                            placeholder="Med Amin "
                        />
                        <MyTextInput
                            label="Email Address"
                            name="email"
                            type="email"
                            placeholder="med@test.com"
                        />
                        <MyTextInput
                            label="Age"
                            name="age"
                            type="number"
                            placeholder="25"
                        />

                        <MySelect label="Gender" name="gender">
                            <option value="">Gender</option>
                            <option value="MALE">Men</option>
                            <option value="FEMALE">Women</option>
                        </MySelect>

                        <Button disabled={!isValid || isSubmitting } mt={"20px"} type="submit">Submit</Button>
                    </Stack>
                </Form>
                )}
            </Formik>
        </>
    );
};

export default  CreateCustomForm ;