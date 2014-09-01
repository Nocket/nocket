This package contains one class for every combination of layouting strategy and
field error validation presentation. The purpose is to check if the masks
all look suitable with and without field errors. Especially the table-layouted
mask with tooltip error presentation is supposed not to show any layout scattering
caused by the error messages.
All attributes which are displayed by input controls are annotated with @NotNull,
so pressing the button() method's button should produce a validation error for
all input fields.
