export const validateForm = (formData,fieldsToValidate = []) => {
    const errors = {};

    // Validate only the fields specified in `fieldsToValidate`
    fieldsToValidate.forEach((field) => {
        switch (field) {
            case 'xwh':
                if (!formData.xwh) {
                    errors.xwh = 'Warehouse is required.';
                }
                break;

            case 'xcus':
                if (!formData.xcus) {
                    errors.xcus = 'Supplier is required.';
                }
                break;

            case 'xitem':
                if (!formData.xitem) {
                    errors.xitem = 'Item is required.';
                }
                break;

            case 'xdate':
                if (!formData.xdate) {
                    errors.xdate = 'Date is required.';
                }
                break;

            case 'xrategrn':
                if (!formData.xrategrn) {
                    errors.xrategrn = 'Rate is required.';
                }
                break;

            case 'xqtygrn':
                if (!formData.xqtygrn) {
                    errors.xqtygrn = 'Quantity is required.';
                }
                break;

            case 'xref':
                if (!formData.xref) {
                    errors.xref = 'Reference is required.';
                }
                break;

            default:
                break;
        }
    });

    return errors;
};
