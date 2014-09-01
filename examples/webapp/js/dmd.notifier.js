// Simplified user notifications based on jQuery Pines Notify (pnotify) Plugin 1.2.0
// Provided by team of Peter Chojecki
// There is a Java-side hook for this functionality in dmdweb.gen.notify.Notifier
var Notifier = function () {
    var self = this;
    var stack_bottomleft = { "dir1": "up", "dir2": "right", "push": "top" };

    var initialize = function () {
        $.pnotify.defaults.history = false;
        $.pnotify.defaults.delay = 3000;
        window.alert = function (p_message) {
            notify('info', 'Nachricht', p_message);
        };
    };

    self.Error = function (p_message) {
        notify('error', 'Fehler', p_message);
    };

    self.Success = function (p_message) {
        notify('success', 'Erfolgreich', p_message);
    };

    self.Info = function (p_message) {
        notify('info', 'Nachricht', p_message);
    };

    var notify = function (p_type, p_heading, p_message) {
        $.pnotify({
            title: p_heading,
            text: p_message,
            addclass: "stack-bottomleft",
            stack: stack_bottomleft,
            type: p_type
        });
    };
    initialize();
    return self;
};
window.Notifier = new Notifier();